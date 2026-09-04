#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s[scores={timer=1..}] timer 1
execute if score @s timer matches ..0 run function nyctocosm:mobs/death_walker/__generated__/block/29