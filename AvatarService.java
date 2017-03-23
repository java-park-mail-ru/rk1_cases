package com.company;

import java.lang.reflect.Constructor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Переписать функции create, changeName и addPerk на Reflection. Т.е. все вызовы и все операции создания объектов (кроме NullPointerException)
 * в этих методах должны быть сделаны через Reflection
 */

public class AvatarService {
    private static Map<Long, Avatar> avatars = new HashMap<>();

    public static final class Avatar {
        private final static AtomicLong ID_GEN = new AtomicLong(0);

        private final Long id;
        private String name;
        private List<Object> perks;

        //serialization constructor
        public Avatar() {
            id = ID_GEN.getAndIncrement();
        }

        public Avatar(String name, List<Object> perks) {
            id = ID_GEN.getAndIncrement();
            this.name = name;
            this.perks = perks;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Object> getPerks() {
            return perks;
        }

        public String toString() {
            return "Id:" + this.id + ", Name: " + this.name + ", Perks: " + this.perks.toString();
        }
    }

    public Avatar create(String name, List<Object> perks) {
        try {
            final Constructor<Avatar> constructor = Avatar.class.getConstructor(String.class, List.class);
            final Avatar avatar = constructor.newInstance(name, perks);
            final Method put = avatars.getClass().getMethod("put", Object.class, Object.class);
            put.invoke(avatars, avatar.id, avatar);

            return avatar;

        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void changeName(long id, String newName) {
        try {
            final Method get = avatars.getClass().getMethod("get", Object.class);
            final Avatar avatar = (Avatar) get.invoke(avatars, id);

            if (avatar == null) {
                throw new NullPointerException("No avatar with id = " + id + " found");
            }

            final Method setName = avatar.getClass().getMethod("setName", String.class);
            setName.invoke(avatar, newName);

        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void addPerk(long id, Object perk) {
        try {
            final Method get = avatars.getClass().getMethod("get", Object.class);
            final Avatar avatar = (Avatar) get.invoke(avatars, id);

            if (avatar == null) {
                throw new NullPointerException("No avatar with id = " + id + " found");
            }

            final Method getPerks = avatar.getClass().getMethod("getPerks");
            ((List<Object>) getPerks.invoke(avatar)).add(perk);

        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void main(String[] args) {
        AvatarService avatarService = new AvatarService();
        System.out.println(avatarService.create("Andrew", new ArrayList<Object>()));
        avatarService.changeName(0, "Test");
        System.out.println(avatars.get(Long.valueOf(0)));
        avatarService.addPerk(0, "perk");
        avatarService.addPerk(0, "perk");
        System.out.println(avatars.get(Long.valueOf(0)));
    }
}

