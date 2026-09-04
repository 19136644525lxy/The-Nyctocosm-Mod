#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score @s l.storyline0 matches 1.. run function lore:storylines/journals/__generated__/block/0
execute if score @s l.storyline0 matches 1 run function lore:storylines/journals/__generated__/block/1
execute if score @s l.storyline0 matches 2 run function lore:storylines/journals/__generated__/block/2
execute if score @s l.storyline0 matches 3 run function lore:storylines/journals/__generated__/block/3
execute if score @s l.storyline0 matches 4 run function lore:storylines/journals/__generated__/block/4
execute unless score @s l.storyline0 matches 4.. run scoreboard players add @s l.storyline0 1