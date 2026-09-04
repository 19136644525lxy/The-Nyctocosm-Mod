#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=emissarymodel] at @s run function emissary:attacks/phase4/idle/__generated__/block/663
scoreboard players set Attacking em.value 1
scoreboard players set Idle em.value 0
kill @e[tag=emissary,tag=flytarget]