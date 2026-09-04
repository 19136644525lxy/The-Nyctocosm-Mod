#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=portal,tag=origin] run function nyctocosm:portal/__generated__/block/46
kill @e[tag=portal]
stopsound @a[distance=..100] master nyctocosm:portal.hum