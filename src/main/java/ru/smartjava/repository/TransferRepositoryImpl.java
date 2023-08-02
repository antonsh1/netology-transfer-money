package ru.smartjava.repository;

import org.springframework.stereotype.Repository;
import ru.smartjava.objects.Transfer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferRepositoryImpl implements TransferRepository {

//    Map<UUID, TransferData> repository = new HashMap<>();
    Map<UUID, Transfer> repository = new ConcurrentHashMap<>();

    public UUID addTransfer(UUID uuid, Transfer transfer) {
        repository.put(uuid, transfer);
        repository.values().forEach(System.out::println);
        System.out.println(repository);
        return uuid;
    }

    public Optional<Transfer> getTransfer(UUID uuid) {
        return Optional.ofNullable(repository.get(uuid));
    }

    public Optional<Transfer> getTransfer(String uuid) {
        return Optional.ofNullable(repository.get(UUID.fromString(uuid)));
    }

    public void updateTransferResult(UUID uuid, String result) {
        repository.get(uuid).setResult(result);
    }

    public void closeTransfer(UUID uuid) {
        repository.get(uuid).setClosed(true);
    }

}
