#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove SerpentPassiveTimer value 1
execute if score SerpentPassiveTimer value matches ..0 run function serpent:__generated__/block/194
execute if entity @a[distance=..130] run scoreboard players remove SerpentAttack timer 1
execute if score SerpentAttack timer matches ..0 run function serpent:__generated__/block/196