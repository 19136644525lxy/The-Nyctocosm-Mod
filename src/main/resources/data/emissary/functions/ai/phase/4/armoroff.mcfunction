#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion_emitter ~ ~ ~ 0 0 0 0 1 force
particle block black_concrete ~ ~ ~ 0.5 1 0.5 0.1 500 force
particle flash ~ ~ ~ 0.5 0.5 0.5 0 10 force
function effects:lightning/explosion_sparks
execute rotated ~ 0 run function effects:shockwave/inculcate