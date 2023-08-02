package ru.smartjava.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smartjava.objects.Transfer;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
public class TransferData {

    private UUID uuid;
    private Date date;
    private Transfer transfer;
    private String commission;
    private String result;

    public TransferData(UUID uuid, Transfer transfer, String result) {
        this.uuid = uuid;
        this.date = new Date();
        this.transfer = transfer;
        this.result = result;
    }

}
