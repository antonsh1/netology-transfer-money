package ru.smartjava.service;

import ru.smartjava.dto.ConfirmMessage;
import ru.smartjava.dto.Transfer;

import java.util.UUID;

public interface TransferService {

    UUID addTransferInfo(Transfer transfer);

    void confirmTransfer(ConfirmMessage confirmMessage);

}
