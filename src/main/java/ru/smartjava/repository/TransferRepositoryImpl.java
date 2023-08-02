package ru.smartjava.repository;

import org.springframework.stereotype.Repository;
import ru.smartjava.objects.Transfer;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

@Repository
public class TransferRepositoryImpl implements TransferRepository {

    Map<UUID, TransferData> repository = new HashMap<>();

    public UUID addTransfer(UUID uuid, Transfer transfer) {
        TransferData newTransferData = new TransferData(uuid, transfer, "");
        repository.put(uuid, newTransferData);
        repository.values().forEach(System.out::println);
        System.out.println(repository);
        return uuid;
    }

    public Optional<Transfer> getTransfer(UUID uuid) {
        return Optional.ofNullable(repository.get(uuid).getTransfer());
    }

    public Optional<Transfer> getTransfer(String uuid) {
        return Optional.ofNullable(repository.get(UUID.fromString(uuid)).getTransfer());
    }

}
