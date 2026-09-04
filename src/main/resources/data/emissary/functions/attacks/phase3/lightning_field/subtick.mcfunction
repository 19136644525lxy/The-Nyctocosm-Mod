#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s em.timer 1
execute as @s[scores={em.timer=..20}] run function emissary:attacks/phase3/lightning_field/__generated__/block/619
execute as @s[scores={em.timer=21..}] at @s run function emissary:attacks/phase3/lightning_field/__generated__/block/620