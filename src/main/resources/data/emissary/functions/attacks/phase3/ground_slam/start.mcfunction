#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=emissarymodel] at @s run function emissary:attacks/phase3/ground_slam/__generated__/block/515
scoreboard players set Attacking em.value 1
scoreboard players set Idle em.value 0