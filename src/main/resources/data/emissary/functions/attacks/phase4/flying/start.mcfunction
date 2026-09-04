#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=emissarymodel] at @s run function emissary:attacks/phase4/flying/__generated__/block/664
scoreboard players set Attacking em.value 1
scoreboard players set Idle em.value 0
scoreboard players set FlySpeed em.value 5