package ru.smartjava.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.smartjava.objects.Transfer;
import ru.smartjava.utils.ObjectsUtils;


class TransferRepositoryImplTests {

    private TransferRepository transferRepository;
    Transfer transfer;
    @BeforeEach
    void initData() {
        transferRepository = new TransferRepositoryImpl();
        transfer = ObjectsUtils.getTransfer();
        transferRepository.addTransfer(ObjectsUtils.uuid, transfer);
    }

    @Test
    void addAndGetTransfer() {
        transferRepository.addTransfer(ObjectsUtils.uuid,transfer);
        Assertions.assertEquals(transferRepository.getTransfer(ObjectsUtils.uuid).get(), transfer);
    }

    @Test
    void updateTransfer() {
        transferRepository.updateTransferResult(ObjectsUtils.uuid,ObjectsUtils.result);
        Assertions.assertEquals(transferRepository.getTransfer(ObjectsUtils.uuid).get().getResult(), ObjectsUtils.result);
        Assertions.assertEquals(transferRepository.getTransfer(ObjectsUtils.uuid).get().getClosed(), true);
    }
}