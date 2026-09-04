#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s em.timer 1
execute store success score @s em.value run data get entity @s attack
execute as @s[scores={em.value=1}] run function emissary:attacks/phase3/swordspin/__generated__/block/506
tag @s add hittest
execute if score @s em.timer matches ..0 as @a[distance=..30,gamemode=!spectator,tag=!emissarydebug] run function emissary:attacks/phase3/swordspin/__generated__/block/507
tag @s remove hittest
kill @s[scores={em.timer=..0}]