#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Sorcerer ns.attackcooldown = temp0 rng
scoreboard players operation Sorcerer ns.attackcooldown /= #100000 rng
scoreboard players operation Sorcerer ns.attackcooldown %= #50 rng
scoreboard players add Sorcerer ns.attackcooldown 10
execute if score Sorcerer ns.phase matches 1 run function sorcerer:ai/__generated__/block/168
execute if score Sorcerer ns.phase matches 2 run function sorcerer:ai/__generated__/block/169
execute if score Sorcerer ns.phase matches 3 run function sorcerer:ai/__generated__/block/170
execute if score Sorcerer ns.phase matches 4 run function sorcerer:ai/__generated__/block/171
execute if score Sorcerer ns.phase matches 5 run function sorcerer:ai/__generated__/block/172
execute if score Sorcerer ns.phase matches 6 run function sorcerer:ai/__generated__/block/173