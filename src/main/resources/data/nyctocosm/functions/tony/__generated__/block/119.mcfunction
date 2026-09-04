#built using mc-build (https://github.com/mc-build/mc-build)

execute align y run tp @s ~ ~ ~
execute at @s run function sorcerer:attacks/ice_spikes/tosurface
execute at @s if entity @a[distance=..10] if block ~ ~ ~ minecraft:air unless block ~ ~-1 ~ #nyctocosm:passthrough run function nyctocosm:tony/__generated__/block/120