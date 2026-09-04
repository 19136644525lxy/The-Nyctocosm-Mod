#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=!z] run particle minecraft:glow ~ ~ ~ 0.1 1 1 0.01 1 force
execute as @s[tag=z] run particle minecraft:glow ~ ~ ~ 1 1 0.1 0.01 1 force
execute as @a[distance=..2] run function nyctocosm:portal/__generated__/block/48