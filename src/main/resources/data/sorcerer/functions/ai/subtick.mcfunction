#built using mc-build (https://github.com/mc-build/mc-build)

execute if score Nullpoint ns.health matches ..0 unless score Sorcerer ns.attacking matches 1.. run function sorcerer:ai/__generated__/block/159
execute if score Sorcerer ns.health matches ..666 if score Sorcerer ns.phase matches 2 unless score Sorcerer ns.attacking matches 1.. run function sorcerer:ai/__generated__/block/160
execute if score Sorcerer ns.health matches ..333 if score Sorcerer ns.phase matches 4 unless score Sorcerer ns.attacking matches 1.. run function sorcerer:ai/__generated__/block/161
execute at @s as @a[distance=..40,tag=!music.sorcerer] at @s run function sorcerer:ai/__generated__/block/162
execute if score Sorcerer ns.health matches ..0 unless score Sorcerer ns.attacking matches 1.. run function sorcerer:defeat