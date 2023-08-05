package ru.smartjava.utils;

import ru.smartjava.dto.*;

import java.util.UUID;

public class ObjectsUtils {

    public static final String uuidString = "38d1e8a8-d74d-4bed-8edc-e92f263c7152";
    public static final String uuidString2 = "38d1e222-d74d-4bed-8edc-e92f263c7152";
    public static final UUID uuid = UUID.fromString(uuidString);

    public static final UUID uuid2 = UUID.fromString(uuidString2);
    public static final String result = "Результат проверки";

    public static Transfer getTransfer() {
        Transfer transfer = new Transfer();
        transfer.setCardFromNumber("1111111111111111");
        transfer.setCardToNumber("2222222222222222");
        transfer.setCardFromCVV("111");
        transfer.setAmount(new Amount());
        transfer.setAmount(getAmount());
        transfer.setCardFromValidTill("11111");
        return transfer;
    }

    public static Transfer getWrongTransfer() {
        Transfer transfer = new Transfer();
        transfer.setCardFromNumber("");
        transfer.setCardToNumber("");
        transfer.setCardFromCVV("111");
        transfer.setAmount(new Amount());
        transfer.setAmount(getAmount());
        transfer.setCardFromValidTill("11111");
        return transfer;
    }

    public static Amount getAmount() {
        Amount amount = new Amount();
        amount.setCurrency("RUR");
        amount.setValue(1000);
        return amount;
    }

    public static ConfirmMessage getConfirmMessage() {
        ConfirmMessage confirmMessage = new ConfirmMessage();
        confirmMessage.setCode(new Code("0000"));
        confirmMessage.setOperationId(new OperationId(uuidString));
        return confirmMessage;
    }

    public static ConfirmMessage getWrongConfirmMessage() {
        ConfirmMessage confirmMessage = new ConfirmMessage();
        confirmMessage.setCode(new Code("0001"));
        confirmMessage.setOperationId(new OperationId(uuidString2));
        return confirmMessage;
    }

    public static OperationId getOperationId() {
        OperationId operationId = new OperationId();
        operationId.setOperationId(uuidString);
        return operationId;
    }
}

