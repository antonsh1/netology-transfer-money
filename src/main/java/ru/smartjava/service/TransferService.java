package ru.smartjava.service;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.smartjava.exceptions.InvalidCode;
import ru.smartjava.exceptions.InvalidOperationId;
import ru.smartjava.exceptions.TransferClosed;
import ru.smartjava.objects.Code;
import ru.smartjava.objects.ConfirmMessage;
import ru.smartjava.objects.OperationId;
import ru.smartjava.objects.Transfer;
import ru.smartjava.repository.TransferRepository;
import ru.smartjava.transferlog.WriteLog;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {

    @Value("${service.default.code:1111}")
    private String DEFAULT_CODE;

    @Value("${service.message.invalid.operationid}")
    private String InvalidOperationIdMessage;

    @Value("${service.message.invalid.code}")
    private String InvalidCodeMessage;

    @Value("${service.message.success}")
    private String SuccessMessage;

    @Value("${service.message.transferclosed}")
    private String TransferClosedMessage;


    private final WriteLog writeLog;

    private final TransferRepository transferRepository;

    public String addTransferInfo(Transfer transfer) {
        UUID uuid = UUID.randomUUID();
        writeLog.printTransfer(uuid, transfer);
        return transferRepository.addTransfer(UUID.randomUUID(), transfer).toString();
    }

    public Optional<OperationId> confirmTransfer(ConfirmMessage confirmMessage) {
        UUID uuid = UUID.fromString(confirmMessage.getOperationId().getOperationId());
        System.out.println(confirmMessage);
        Optional<Transfer> transfer = transferRepository.getTransfer(confirmMessage.getOperationId().getOperationId());
        if (transfer.isEmpty()) {
            throw new InvalidOperationId(InvalidOperationIdMessage);
        }
        if (transfer.get().getClosed()) {
            throw new TransferClosed(TransferClosedMessage);
        }
        transferRepository.closeTransfer(uuid);
        if (!checkCode(confirmMessage.getCode())) {
            transferRepository.updateTransferResult(uuid, InvalidCodeMessage);
            writeLog.printResult(uuid, InvalidCodeMessage);
            throw new InvalidCode(InvalidCodeMessage);
        }
        transferRepository.updateTransferResult(uuid, SuccessMessage);
        writeLog.printResult(uuid, SuccessMessage);
        return Optional.of(confirmMessage.getOperationId());
    }

    public Boolean checkCode(Code code) {
        System.out.println(code);
        System.out.println(DEFAULT_CODE);
        return Objects.equals(code.getCode(), DEFAULT_CODE);
    }
}
