package ru.smartjava.repository;

import org.springframework.stereotype.Repository;
import ru.smartjava.dto.Transfer;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransferRepositoryImpl implements TransferRepository {

    private final Map<UUID, Transfer> repository = new ConcurrentHashMap<>();

    public UUID addTransfer(UUID uuid, Transfer transfer) {
        repository.put(uuid, transfer);
        return uuid;
    }

    public Optional<Transfer> getTransfer(UUID uuid) {
        return Optional.ofNullable(repository.get(uuid));
    }

    public void updateTransferResult(UUID uuid, String result) {
        repository.get(uuid).setClosed(true);
        repository.get(uuid).setResult(result);
    }

}
