#built using mc-build (https://github.com/mc-build/mc-build)

execute if score @s[predicate=lore:holding_storyline_0] l.uselorebook matches 1.. run function lore:storylines/journals/nextentry
execute if score @s[predicate=lore:holding_storyline_1] l.uselorebook matches 1.. run function lore:storylines/catechism/nextentry
execute if score @s[predicate=lore:holding_storyline_2] l.uselorebook matches 1.. run function lore:storylines/dreams/nextentry
execute if score @s[predicate=lore:holding_storyline_3] l.uselorebook matches 1.. run function lore:storylines/axiotopology/nextentry
scoreboard players reset @s l.uselorebook