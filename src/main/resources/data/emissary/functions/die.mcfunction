#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=emissarymodel] at @s run function emissary:__generated__/block/31
scoreboard players set Attacking em.value 1
scoreboard players reset Flying em.value
scoreboard players set Idle em.value 0
execute as @a[tag=music.emissary] run function music:emissary/end