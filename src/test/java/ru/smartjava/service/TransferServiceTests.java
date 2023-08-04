package ru.smartjava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.smartjava.objects.ConfirmMessage;
import ru.smartjava.objects.Transfer;
import ru.smartjava.repository.TransferRepository;
import ru.smartjava.repository.TransferRepositoryImpl;
import ru.smartjava.transferlog.WriteLog;
import ru.smartjava.utils.ObjectsUtils;

import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class TransferServiceTests {

    @Mock
    private WriteLog writeLog;
    private TransferRepository transferRepository;
    private TransferService transferService;
    private Transfer transfer;
    ConfirmMessage confirmMessage;


    @BeforeEach
    void initData() {

        transferRepository = new TransferRepositoryImpl();
        transferService = new TransferService(writeLog, transferRepository);

        transfer = ObjectsUtils.getTransfer();

        confirmMessage = ObjectsUtils.getConfirmMessage();

        transferRepository.addTransfer(ObjectsUtils.uuid, transfer);

    }

    @Test
    void add() {
        UUID uuid = transferService.addTransferInfo(transfer);
        Assertions.assertEquals(transferRepository.getTransfer(uuid).get(), transfer);
    }

    @Test
    void confirm() {
        System.out.println(confirmMessage);
        transferService.confirmTransfer(confirmMessage);
        Assertions.assertEquals(transferRepository.getTransfer(ObjectsUtils.uuid).get().getClosed(), true);
    }

}