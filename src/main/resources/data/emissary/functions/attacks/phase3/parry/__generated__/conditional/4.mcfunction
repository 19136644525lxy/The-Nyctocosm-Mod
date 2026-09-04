#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset Poise em.value
execute as @e[tag=emissarymodel] at @s run function emissary:attacks/phase3/parry/__generated__/block/463
particle firework ~ ~ ~ 0.1 0.1 0.1 0.1 5 force
particle flash ~ ~ ~ 0 0 0 0 1 force
execute rotated ~ 90 run function effects:shockwave/parrybreak
scoreboard players set #execute LANG_MC_INTERNAL 1