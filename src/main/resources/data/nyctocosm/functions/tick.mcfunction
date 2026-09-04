#built using mc-build (https://github.com/mc-build/mc-build)

execute as @a at @s run function nyctocosm:__generated__/block/0
execute in nyctocosm:nyctocosm run function nyctocosm:mobs/spawning
function nyctocosm:mobs/mobs_tick
function nyctocosm:portal/portal_tick
execute as @e[tag=tony] at @s run function nyctocosm:tony/subtick
execute store result score Systick time in nyctocosm:nyctocosm run worldborder get
scoreboard players operation 0 temp = Systick time
scoreboard players operation 0 temp %= #1000000 const
scoreboard players operation Systick time = 0 temp
execute in nyctocosm:nyctocosm run worldborder set 49000000
execute in nyctocosm:nyctocosm run worldborder set 59000000 10000