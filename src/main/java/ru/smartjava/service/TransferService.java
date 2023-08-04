package ru.smartjava.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.smartjava.exceptions.InvalidCode;
import ru.smartjava.exceptions.InvalidOperationId;
import ru.smartjava.exceptions.TransferClosed;
import ru.smartjava.objects.Code;
import ru.smartjava.objects.ConfirmMessage;
import ru.smartjava.objects.Transfer;
import ru.smartjava.repository.TransferRepository;
import ru.smartjava.transferlog.WriteLog;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class TransferService {

    public String DEFAULT_CODE = "0000";

    @Value("${service.message.invalid.operationid}")
    public String InvalidOperationIdMessage;

    @Value("${service.message.invalid.code}:")
    public String InvalidCodeMessage;

    @Value("${service.message.success}")
    public String SuccessMessage;

    @Value("${service.message.transferclosed}")
    public String TransferClosedMessage;

    private final WriteLog writeLog;

    private final TransferRepository transferRepository;

    public UUID addTransferInfo(Transfer transfer) {
        UUID uuid = UUID.randomUUID();
//        System.out.println("ADD " + transfer);
        writeLog.printTransfer(uuid, transfer);
        return transferRepository.addTransfer(uuid, transfer);
    }

    public void confirmTransfer(ConfirmMessage confirmMessage) {
        UUID uuid = UUID.fromString(confirmMessage.getOperationId().getOperationId());
//        System.out.println("CHECK " + confirmMessage);
        Optional<Transfer> transfer = transferRepository.getTransfer(
                UUID.fromString(confirmMessage.getOperationId().getOperationId())
        );
        if (transfer.isEmpty()) {
            throw new InvalidOperationId(InvalidOperationIdMessage);
        }
        if (transfer.get().getClosed()) {
            throw new TransferClosed(TransferClosedMessage);
        }
        if (!checkCode(confirmMessage.getCode())) {
            transferRepository.updateTransferResult(uuid, InvalidCodeMessage);
            writeLog.printResult(uuid, InvalidCodeMessage);
            throw new InvalidCode(InvalidCodeMessage);
        }
        transferRepository.updateTransferResult(uuid, SuccessMessage);
        writeLog.printResult(uuid, SuccessMessage);
    }

    public Boolean checkCode(Code code) {
        return Objects.equals(code.getCode(), DEFAULT_CODE);
    }
}
