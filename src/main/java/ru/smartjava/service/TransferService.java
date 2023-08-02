package ru.smartjava.service;

import java.awt.*;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smartjava.objects.Code;
import ru.smartjava.objects.ConfirmMessage;
import ru.smartjava.objects.OperationId;
import ru.smartjava.objects.Transfer;
import ru.smartjava.repository.TransferRepository;

import javax.swing.text.html.Option;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class TransferService {

//    Logger logger = Logger.getLogger(TransferService.class.getName());
    private final String DEFAULT_CODE = "0000";

    private final TransferRepository transferRepository;

    @Autowired
    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public String addTransferInfo(Transfer transfer) {
        UUID newUUID = UUID.randomUUID();
//        logger.info("sdfgsdfgsdfgs");
        return transferRepository.addTransfer(newUUID, transfer).toString();
    }

    public Optional<OperationId> confirmTransfer(ConfirmMessage confirmMessage) {
        System.out.println(confirmMessage);
            Optional<Transfer> transfer = transferRepository.getTransfer(confirmMessage.getOperationId().getOperationId());
            if(transfer.isPresent()) {
                return Optional.of(confirmMessage.getOperationId());
            } else {
                return Optional.empty();
            }
    }

    public Boolean checkCode(Code code) {
        return Objects.equals(code.getCode(), DEFAULT_CODE);
    }
}
