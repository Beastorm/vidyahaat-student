package com.vidyahaat.model.channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelModel {

    String ChannelName;
    String noOfPeopleWatching;

    public ChannelModel(String channelName, String noOfPeopleWatching) {
        ChannelName = channelName;
        this.noOfPeopleWatching = noOfPeopleWatching;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getNoOfPeopleWatching() {
        return noOfPeopleWatching;
    }

    public void setNoOfPeopleWatching(String noOfPeopleWatching) {
        this.noOfPeopleWatching = noOfPeopleWatching;
    }

    public static List<ChannelModel> getAllChannel() {
        List<ChannelModel> channels = new ArrayList<ChannelModel>();

        channels.add(new ChannelModel("Math", "33"));
        channels.add(new ChannelModel("English", "33"));
        channels.add(new ChannelModel("Chem", "33"));
        channels.add(new ChannelModel("Physics", "33"));
        channels.add(new ChannelModel("Hindi", "33"));


        return channels;
    }
}
