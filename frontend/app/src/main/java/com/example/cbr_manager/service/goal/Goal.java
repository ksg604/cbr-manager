package com.example.cbr_manager.service.goal;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.utils.CBRTimestamp;
import com.example.cbr_manager.utils.Helper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

@Entity(tableName = "goal")
public class Goal{
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "goal_id")
    private Integer id;

    @SerializedName("datetime_created")
    @Expose
    private Timestamp datetimeCreated;

    @SerializedName("datetime_completed")
    @Expose
    private Timestamp datetimeCompleted;

    @SerializedName("user_creator")
    @Expose
    private int userId;

    @SerializedName("client")
    @Expose
    @Embedded
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    private Client client;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("is_initial_goal")
    @Expose
    private boolean is_initial_goal;

    @SerializedName("status")
    @Expose
    private String status;

    public Goal() {
        this.id = 0;
        this.userId = 0;
        this.client = new Client();
        this.category = "";
        this.title = "";
        this.description = "";
        this.is_initial_goal = false;
        this.status = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDatetimeCreated() {
        return datetimeCreated;
    }

    public void setDatetimeCreated(Timestamp datetimeCreated) {
        this.datetimeCreated = datetimeCreated;
    }

    public Timestamp getDatetimeCompleted() {
        return datetimeCompleted;
    }

    public void setDatetimeCompleted(Timestamp datetimeCompleted) {
        this.datetimeCompleted = datetimeCompleted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_initial_goal() {
        return is_initial_goal;
    }

    public void setIs_initial_goal(boolean is_initial_goal) {
        this.is_initial_goal = is_initial_goal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
