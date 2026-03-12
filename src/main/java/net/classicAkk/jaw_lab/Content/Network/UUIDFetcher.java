package net.classicAkk.jaw_lab.Content.Network;

import com.mojang.authlib.GameProfile;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UUIDFetcher {

    public static CompletableFuture<UUID> getPlayerUUIDAsync(MinecraftServer server, String nickname) {
        return CompletableFuture.supplyAsync(() -> {
            ServerPlayer onlinePlayer = server.getPlayerList().getPlayerByName(nickname);
            if (onlinePlayer != null) {
                return onlinePlayer.getUUID();
            }

            Optional<GameProfile> profile = server.getProfileCache().get(nickname);
            if (profile.isPresent()) {
                return profile.get().getId();
            }

            if (!server.usesAuthentication()) {
                return UUID.nameUUIDFromBytes(("OfflinePlayer:" + nickname).getBytes(StandardCharsets.UTF_8));
            }

            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + nickname);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                if (connection.getResponseCode() == 200) {
                    JsonObject json = JsonParser.parseReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))
                            .getAsJsonObject();
                    String id = json.get("id").getAsString();
                    return UUID.fromString(id.replaceFirst(
                            "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                            "$1-$2-$3-$4-$5"
                    ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        });
    }
}