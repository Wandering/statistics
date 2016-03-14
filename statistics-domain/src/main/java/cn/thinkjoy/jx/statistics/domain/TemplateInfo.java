package cn.thinkjoy.jx.statistics.domain;

/**
 * Created by Mike on 2015/12/22.
 */
public class TemplateInfo {
    private int templateId;
    private String templateName;
    private long importDate;
    private long accountId;
    private int status;
    private int errorData;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public long getImportDate() {
        return importDate;
    }

    public void setImportDate(long importDate) {
        this.importDate = importDate;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorData() {
        return errorData;
    }

    public void setErrorData(int errorData) {
        this.errorData = errorData;
    }
}
