/*
 * Copyright (c) 2020 amy, All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.mewna.catnip.entity.interaction;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.mewna.catnip.Catnip;
import com.mewna.catnip.entity.message.Embed;
import com.mewna.catnip.entity.message.MentionParseFlag;
import com.mewna.catnip.entity.message.MessageFlag;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * @author amy
 * @since 12/10/20.
 */
public interface InteractionApplicationCommandCallbackData {
    boolean tts();
    
    String content();
    
    List<Embed> embeds();
    
    Set<MentionParseFlag> allowedMentions();
    
    Set<MessageFlag> flags();
    
    default JsonObject toJson(@Nonnull final Catnip catnip) {
        final JsonObject allowedMentions = new JsonObject();
        if(allowedMentions() != null && !allowedMentions().isEmpty()) {
            final EnumSet<MentionParseFlag> parse = EnumSet.copyOf(allowedMentions());
            final JsonArray parseList = new JsonArray();
            for(final MentionParseFlag p : parse) {
                parseList.add(p.flagName());
            }
            allowedMentions.put("parse", parseList);
        }
        
        final var builder = JsonObject.builder();
        builder.value("tts", tts());
        builder.value("content", content());
        builder.value("embeds", JsonArray.from(embeds().stream()
                .map(catnip.entityBuilder()::embedToJson)
                .toArray(Object[]::new)));
        builder.value("allowed_mentions", allowedMentions);
        builder.value("flags", MessageFlag.fromSettable(flags()));
        return builder.done();
    }
}
