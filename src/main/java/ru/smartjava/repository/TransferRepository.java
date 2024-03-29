package ru.smartjava.repository;

import org.springframework.stereotype.Repository;
import ru.smartjava.dto.Transfer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository {

    UUID addTransfer(UUID uuid, Transfer transfer);

    Optional<Transfer> getTransfer(UUID uuid);

    void updateTransferResult(UUID uuid, String result);

}
