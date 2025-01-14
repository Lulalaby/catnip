/*
 * Copyright (c) 2021 amy, All rights reserved.
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

package com.mewna.catnip.entity.builder.command;

import com.mewna.catnip.entity.impl.interaction.ApplicationCommandOptionIntegerChoiceImpl;
import com.mewna.catnip.entity.impl.interaction.ApplicationCommandOptionStringChoiceImpl;
import com.mewna.catnip.entity.interaction.ApplicationCommandOptionChoice;
import com.mewna.catnip.util.Validators;

import javax.annotation.Nonnull;

/**
 * @author amy
 * @since 12/11/20.
 */
public class CommandOptionChoiceBuilder<T> {
    private String name;
    private T value;
    
    public CommandOptionChoiceBuilder<T> name(@Nonnull final String name) {
        this.name = name;
        return this;
    }
    
    public CommandOptionChoiceBuilder<T> value(@Nonnull final T value) {
        this.value = value;
        return this;
    }
    
    @SuppressWarnings("unchecked")
    public ApplicationCommandOptionChoice<T> build() {
        Validators.assertStringLength(name, "name", 3, 32);
        Validators.assertType(value, new Class[]{String.class, Integer.class}, "data");
        // TODO: Convert this into instanceof pattern matching when Java 16 comes out
        if(value instanceof String) {
            return (ApplicationCommandOptionChoice<T>) ApplicationCommandOptionStringChoiceImpl.builder()
                    .name(name)
                    .value((String) value)
                    .build();
        } else if(value instanceof Integer) {
            return (ApplicationCommandOptionChoice<T>) ApplicationCommandOptionIntegerChoiceImpl.builder()
                    .name(name)
                    .value((Integer) value)
                    .build();
        } else {
            return Validators.unreachable();
        }
    }
}
