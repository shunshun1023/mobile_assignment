package com.df.dianping;

/**
 * Represents an item in a ToDo list
 */
public class ToDoItem {

    /**
     * Item text
     */
    @com.google.gson.annotations.SerializedName("text")
    private String mText;

    /**
     * Item Id
     */
    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    /**
     * Indicates if the item is completed
     */
    @com.google.gson.annotations.SerializedName("complete")
    private boolean mComplete;

    @com.google.gson.annotations.SerializedName("restaurant")
    private String mRestaurant;

    @com.google.gson.annotations.SerializedName("rank")
    private float mRank;

    @com.google.gson.annotations.SerializedName("tasteRank")
    private float mTasteRank;

    @com.google.gson.annotations.SerializedName("environmentRank")
    private float mEnvironmentRank;

    @com.google.gson.annotations.SerializedName("serviceRank")
    private float mServiceRank;

    /**
     * ToDoItem constructor
     */
    public ToDoItem() {

    }

    @Override
    public String toString() {
        return getText();
    }

    /**
     * Initializes a new ToDoItem
     *
     * @param text
     *            The item text
     * @param id
     *            The item id
     */
    public ToDoItem(String text, String id) {
        this.setText(text);
        this.setId(id);
    }

    /**
     * Returns the item text
     */
    public String getText() {
        return mText;
    }

    /**
     * Sets the item text
     *
     * @param text
     *            text to set
     */
    public final void setText(String text) {
        mText = text;
    }

    /**
     * Returns the item id
     */
    public String getId() {
        return mId;
    }

    public float getRank() {
        return mRank;
    }

    public float gettasteRank() { return mTasteRank; }

    public float getEnvironmentRank() {
        return mEnvironmentRank;
    }

    public float getServiceRank() {
        return mServiceRank;
    }


    /**
     * Sets the item id
     *
     * @param id
     *            id to set
     */
    public final void setId(String id) {
        mId = id;
    }

    /**
     * Indicates if the item is marked as completed
     */
    public boolean isComplete() {
        return mComplete;
    }

    /**
     * Marks the item as completed or incompleted
     */
    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    public final void setResaurant(String name) {
        mRestaurant = name;
    }
    public final void setRank(float rank) { mRank = rank; }
    public final void setTasteRank(float rank) { mTasteRank = rank; }
    public final void setEnvironmentRank(float rank) { mEnvironmentRank = rank; }
    public final void setServiceRank(float rank) { mServiceRank = rank; }

    @Override
    public boolean equals(Object o) {
        return o instanceof ToDoItem && ((ToDoItem) o).mId == mId;
    }
}