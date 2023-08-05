package ru.smartjava.transferlog;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.smartjava.dto.Transfer;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Configuration
@Setter
@RequiredArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WriteLog {

    @Value("${writelog.filename:defaulttransfer.log}")
    private String fileName;

    @Value("${writelog.path:c:/}")
    private String path;

    private synchronized void write(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + fileName, true))){
            writer.append(message);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void printTransfer(UUID uuid, Transfer transfer) {
        String message = String.format(
                "Transfer [%s] [%s] [SourceCard:%s] [CVV:%s] [Valid:%s] [DestinationCard:%s] [Currency:%s] [Value:%s]%n",
                uuid, transfer.getDate().toString(), transfer.getCardFromNumber(), transfer.getCardFromCVV(),
                transfer.getCardFromValidTill(), transfer.getCardToNumber(), transfer.getAmount().getCurrency(), transfer.getAmount().getValue()
        );
        write(message);
    }


    public void printResult(UUID uuid, String result) {
        String message = String.format(
                "Transfer Result [%s] [%s]%n", uuid.toString(), result
        );
        write(message);
    }

    public synchronized void simplePrint(String value) {
        write(value);
    }

}
