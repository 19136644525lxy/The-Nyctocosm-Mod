#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation SerpentAttackTrigger value = temp0 rng
scoreboard players operation SerpentAttackTrigger value /= #100000 rng
scoreboard players operation SerpentAttackTrigger value %= #3 rng
scoreboard players add SerpentAttackTrigger value 1
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation SerpentAttack timer = temp0 rng
scoreboard players operation SerpentAttack timer /= #100000 rng
scoreboard players operation SerpentAttack timer %= #100 rng
scoreboard players add SerpentAttack timer 100
execute if score SerpentAttackTrigger value matches 1 run function serpent:attack/dive/start
execute if score SerpentAttackTrigger value matches 2 run function serpent:attack/laser/start
execute if score SerpentAttackTrigger value matches 3 run function serpent:attack/beam/start
scoreboard players reset SerpentAttackTrigger value