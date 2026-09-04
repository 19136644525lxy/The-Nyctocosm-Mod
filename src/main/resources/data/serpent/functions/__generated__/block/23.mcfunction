#built using mc-build (https://github.com/mc-build/mc-build)

playsound nyctocosm:serpent.hurt master @a[distance=..300] ~ ~ ~ 50
execute store result score @s s.damageTaken run data get entity @s Health 1
scoreboard players set 1024 value 1024
scoreboard players operation 1024 value -= @s s.damageTaken
scoreboard players operation @s s.damageTaken = 1024 value
scoreboard players operation SerpentHealth value -= @s s.damageTaken
scoreboard players reset 1024 value
scoreboard players reset @s s.damageTaken
data merge entity @s {Health:1024}
execute store result bossbar minecraft:serpent value run scoreboard players get SerpentHealth value
bossbar set minecraft:serpent color white
schedule function serpent:__generated__/sequence/1 2t replace
schedule function serpent:__generated__/sequence/2 4t replace
schedule function serpent:__generated__/sequence/3 6t replace