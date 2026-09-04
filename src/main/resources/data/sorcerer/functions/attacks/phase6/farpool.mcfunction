#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation Sorcerer ns.attack = temp0 rng
scoreboard players operation Sorcerer ns.attack /= #100000 rng
scoreboard players operation Sorcerer ns.attack %= #7 rng
scoreboard players add Sorcerer ns.attack 0
execute if score Sorcerer ns.attack matches 0..1 run function sorcerer:attacks/dashstrike/start
execute if score Sorcerer ns.attack matches 2..3 run function sorcerer:attacks/airdash/start
execute if score Sorcerer ns.attack matches 4..6 run function sorcerer:attacks/voidbeam/start
scoreboard players reset Sorcerer ns.attack