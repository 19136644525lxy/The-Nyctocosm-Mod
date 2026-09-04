#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ ~
tag @s add emissary
tag @s add spawntarget
execute at @s run playsound nyctocosm:emissary.spawn.meteor master @a[distance=..300] ~ ~ ~ 50
execute at @s rotated ~ 50 positioned ^ ^ ^-100 run function emissary:__generated__/block/27