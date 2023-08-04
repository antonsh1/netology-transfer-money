package ru.smartjava.transferlog;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.smartjava.objects.Transfer;

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

    private FileWriter fileWriter;

    private BufferedWriter out;

    public synchronized void init() {
        try {
            this.fileWriter = new FileWriter(path + fileName, true);
            this.out = new BufferedWriter(this.fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void printTransfer(UUID uuid, Transfer transfer) {
        if (out == null) init();
        String message = String.format(
                "Transfer [%s] [%s] [SourceCard:%s] [CVV:%s] [Valid:%s] [DestinationCard:%s] [Currency:%s] [Value:%s]%n",
                uuid, transfer.getDate().toString(), transfer.getCardFromNumber(), transfer.getCardFromCVV(),
                transfer.getCardFromValidTill(), transfer.getCardToNumber(), transfer.getAmount().getCurrency(), transfer.getAmount().getValue()
        );
        try {
            out.append(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized void printResult(UUID uuid, String result) {
        if (out == null) init();
        String message = String.format(
                "Transfer Result [%s] [%s]%n", uuid.toString(), result
        );
        try {
            out.append(message);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void simplePrint(String value) {
        if (out == null) init();
        try {
            out.append(value);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
