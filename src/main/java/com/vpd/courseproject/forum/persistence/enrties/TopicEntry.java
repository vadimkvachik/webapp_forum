package com.vpd.courseproject.forum.persistence.enrties;

import com.vpd.courseproject.forum.persistence.entity.Message;
import com.vpd.courseproject.forum.persistence.entity.Topic;

public class TopicEntry implements Comparable<TopicEntry> {
    private Topic topic;
    private long numberOfMessages;
    private Message lastMessage;
    private String lastMessagePreview;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public long getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(long numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessagePreview() {
        return lastMessagePreview;
    }

    public void setLastMessagePreview(String lastMessagePreview) {
        this.lastMessagePreview = lastMessagePreview;
    }

    @Override
    public int compareTo(TopicEntry o) {
        if (lastMessage != null && o.lastMessage != null) {
            return (int) (o.lastMessage.getId() - lastMessage.getId());
        } else if (lastMessage != null) {
            return -1;
        } else if (o.lastMessage != null) {
            return 1;
        } else{
            return (int)(o.topic.getId() - topic.getId());
        }

    }

}
