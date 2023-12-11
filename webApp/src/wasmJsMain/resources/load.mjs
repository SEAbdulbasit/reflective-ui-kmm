import { instantiate } from './reflective-ui-kmm.uninstantiated.mjs';

await wasmSetup;

instantiate({ skia: Module['asm'] });
