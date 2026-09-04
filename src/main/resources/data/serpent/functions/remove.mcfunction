#built using mc-build (https://github.com/mc-build/mc-build)

execute as @e[tag=hitbox,tag=serpent] run function serpent:__generated__/block/33
kill @e[tag=serpent]
scoreboard players reset SerpentHealth value
scoreboard players reset SerpentState value
tag @a remove serpenttarget
tag @a remove serpentmusicplaying
tag @a remove s.damagedSerpent
bossbar set minecraft:serpent players