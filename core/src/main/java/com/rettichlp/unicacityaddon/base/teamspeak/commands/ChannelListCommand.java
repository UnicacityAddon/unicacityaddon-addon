package com.rettichlp.unicacityaddon.base.teamspeak.commands;

import com.rettichlp.unicacityaddon.UnicacityAddon;
import com.rettichlp.unicacityaddon.base.teamspeak.CommandResponse;
import com.rettichlp.unicacityaddon.base.teamspeak.objects.Channel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class ChannelListCommand extends BaseCommand<ChannelListCommand.Response> {

    public ChannelListCommand(UnicacityAddon unicacityAddon) {
        super(unicacityAddon, "channellist");
    }

    public static class Response extends CommandResponse {

        private final List<Channel> channels = new ArrayList<>();

        public Response(String rawResponse) {
            super(rawResponse);

            List<Map<String, String>> maps = getResponseList();

            for (Map<String, String> channelMap : maps) {
                channels.add(new Channel(channelMap));
            }

            Collections.sort(channels);
        }

        public List<Channel> getChannels() {
            return channels;
        }
    }
}
