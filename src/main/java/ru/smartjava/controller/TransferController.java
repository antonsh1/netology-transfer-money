package ru.smartjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.smartjava.objects.ConfirmMessage;
import ru.smartjava.objects.OperationId;
import ru.smartjava.objects.Transfer;
import ru.smartjava.service.TransferService;
import ru.smartjava.transferlog.WriteLog;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class TransferController {

    private final TransferService transferService;
    private final WriteLog writeLog;

    @Autowired
    public TransferController(TransferService transferService, WriteLog writeLog) {
        this.transferService = transferService;
        this.writeLog = writeLog;
    }

    @GetMapping("/check")
    public String checker() {
        return "Check Okay";
    }

    @PostMapping("/transfer")
    public OperationId addTransferInfo(@Valid @RequestBody Transfer transfer) {
//        writeLog.showLogFile();
        return new OperationId(transferService.addTransferInfo(transfer));
    }

    @PostMapping("/confirmOperation")
    public OperationId confirmCode(@Valid @RequestBody ConfirmMessage confirmMessage) {
//        writeLog.showLogFile();
        Optional<OperationId> operationId = transferService.confirmTransfer(confirmMessage);
//        if (operationId.isEmpty()) {
//            throw new InvalidOperationId("Неверный код транзакции");
//        }
//        if (!transferService.checkCode(confirmMessage.getCode())) {
//            throw new InvalidCode("Неверный код подтверждения");
//        }
        return confirmMessage.getOperationId();
    }
}
