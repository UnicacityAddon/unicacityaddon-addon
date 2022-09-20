package com.rettichlp.UnicacityAddon.base.api.json;

import java.util.List;

// TODO: 20.09.2022
public class ErrorLog {

    private List<ErrorLogEntry> errorLogEntryList;

    public ErrorLog() {
    }

    public List<ErrorLogEntry> getErrorLogEntryList() {
        return errorLogEntryList;
    }

    public void setErrorLogEntryList(List<ErrorLogEntry> errorLogEntryList) {
        this.errorLogEntryList = errorLogEntryList;
    }
}