package ru.smartjava.repository;

import org.springframework.stereotype.Repository;
import ru.smartjava.objects.Transfer;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository {

    UUID addTransfer(UUID uuid, Transfer transfer);

    Optional<Transfer> getTransfer(UUID uuid);

    Optional<Transfer> getTransfer(String uuid);

}
