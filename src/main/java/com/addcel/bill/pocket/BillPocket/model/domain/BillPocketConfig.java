package com.addcel.bill.pocket.BillPocket.model.domain;

//@Entity
//@Table(name = "BILLPOCKET_CONFIG")
public class BillPocketConfig {

//    @Id
//    @Column(name = "ID")
    private Integer id;

//    @Column(name = "API_KEY")
    private String apiKey;

//    @Column(name = "URL")
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
