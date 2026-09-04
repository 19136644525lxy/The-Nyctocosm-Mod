#built using mc-build (https://github.com/mc-build/mc-build)

execute if score SerpentState value matches 5 as @e[tag=serpent,tag=head] run function serpent:__generated__/block/0
execute as @e[tag=serpent] at @s run function serpent:__generated__/block/6