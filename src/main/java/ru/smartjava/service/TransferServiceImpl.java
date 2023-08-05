package ru.smartjava.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.smartjava.dto.ConfirmMessage;
import ru.smartjava.dto.Transfer;
import ru.smartjava.exceptions.InvalidCode;
import ru.smartjava.exceptions.InvalidOperationId;
import ru.smartjava.exceptions.TransferClosed;
import ru.smartjava.repository.TransferRepository;
import ru.smartjava.transferlog.WriteLog;
import ru.smartjava.utils.ConfirmCode;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Setter
public class TransferServiceImpl implements TransferService {

    @Value("${service.message.invalid.operationid}")
    public String InvalidOperationIdMessage;

    @Value("${service.message.invalid.code}:")
    public String InvalidCodeMessage;

    @Value("${service.message.success}")
    public String SuccessMessage;

    @Value("${service.message.transferclosed}")
    public String TransferClosedMessage;

    private final ConfirmCode confirmCode;

    private final WriteLog writeLog;

    private final TransferRepository transferRepository;

    public UUID addTransferInfo(Transfer transfer) {
        UUID uuid = UUID.randomUUID();
        writeLog.printTransfer(uuid, transfer);
        return transferRepository.addTransfer(uuid, transfer);
    }

    public void confirmTransfer(ConfirmMessage confirmMessage) {
        UUID uuid = UUID.fromString(confirmMessage.getOperationId().getOperationId());
        Optional<Transfer> transfer = transferRepository.getTransfer(
                UUID.fromString(confirmMessage.getOperationId().getOperationId())
        );
        if (transfer.isEmpty()) {
            throw new InvalidOperationId(InvalidOperationIdMessage);
        }
        if (transfer.get().getClosed()) {
            throw new TransferClosed(TransferClosedMessage);
        }
        if (!confirmCode.isGood(confirmMessage.getCode())) {
            transferRepository.updateTransferResult(uuid, InvalidCodeMessage);
            writeLog.printResult(uuid, InvalidCodeMessage);
            throw new InvalidCode(InvalidCodeMessage);
        }
        transferRepository.updateTransferResult(uuid, SuccessMessage);
        writeLog.printResult(uuid, SuccessMessage);
    }


}
