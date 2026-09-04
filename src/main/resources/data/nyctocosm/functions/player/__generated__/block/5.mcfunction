#built using mc-build (https://github.com/mc-build/mc-build)

effect give @s darkness 5 0 true
effect give @s slowness 1 1 true
scoreboard players add @s heartbeattimer 1
execute if score @s heartbeattimer >= @s calmness run function nyctocosm:player/__generated__/block/6
scoreboard players remove @s feartimer 1
scoreboard players reset @s[scores={feartimer=..0}] feartimer
execute unless score @s feartimer matches 1.. run function nyctocosm:player/__generated__/block/7