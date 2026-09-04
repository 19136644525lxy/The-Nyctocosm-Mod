#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s em.timer 1
tag @s add hittest
execute if score @s em.timer matches ..0 as @a[distance=..30,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase3/ground_slam/__generated__/block/517
tag @s remove hittest
kill @s[scores={em.timer=..0}]