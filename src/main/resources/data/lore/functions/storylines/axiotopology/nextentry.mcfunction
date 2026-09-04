#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score @s l.storyline3 matches 1.. run function lore:storylines/axiotopology/__generated__/block/9
execute if score @s l.storyline3 matches 1 run function lore:storylines/axiotopology/__generated__/block/10
execute unless score @s l.storyline3 matches 2.. run scoreboard players add @s l.storyline3 1